/*
 * **************************************************************************
 *   Author: Luis Octavio Goméz de la Cruz
 *   Date created: 22/01/23 13:56
 *   File: UserFragment.kt
 *   Last modified: 22/01/23 12:11
 *   Last change:
 *     Change Name ->
 *     Change Date -> 22/01/23 13:56
 *     Description ->
 *
 * **************************************************************************
 */

package com.luocz.autoacuerdate.views.fragments

import android.content.DialogInterface
import android.content.SharedPreferences
import android.content.res.ColorStateList
import android.content.res.Configuration
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.os.bundleOf
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.luocz.autoacuerdate.R
import com.luocz.autoacuerdate.databinding.FragmentUserBinding
import com.luocz.autoacuerdate.utils.Constants
import com.luocz.autoacuerdate.views.activities.psw
import java.io.IOException
import java.security.GeneralSecurityException


class UserFragment : Fragment(R.layout.fragment_user) {
    private lateinit var binding: FragmentUserBinding

    private lateinit var encryptedSharedPreferences: EncryptedSharedPreferences
    private lateinit var encryptedSharedPrefsEditor: SharedPreferences.Editor
    private lateinit var firebaseAuth: FirebaseAuth
    private var user: FirebaseUser? = null
    private var userId: String? = null
    private var flagEmailVerificated = true
    private var flagIdBiometricActived = false

    //    private var psw = ""
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentUserBinding.bind(view)
        setupMenu()


//                Firebase
        firebaseAuth = FirebaseAuth.getInstance()
        user = firebaseAuth?.currentUser
        userId = user?.uid

        binding.tvUser.text = "HI! "+user?.email

//        Revisamos que el Email no esta verificado
        if (user?.isEmailVerified != true) {
            flagEmailVerificated = false
            user?.sendEmailVerification()?.addOnSuccessListener {
                Toast.makeText(
                    requireContext(),
                    "El correo de verificación ha sido enviado a su correo registrado",
                    Toast.LENGTH_SHORT
                ).show()
            }?.addOnFailureListener {
                Toast.makeText(
                    requireContext(),
                    "Error: El correo de verificación no se ha podido enviar, intentelo en otro momento",
                    Toast.LENGTH_SHORT
                ).show()
                Log.d("ERRORAPP", "onFailure: ${it.message}")
            }
        }
//        Para encrypytar las shared Prefrences
        try {
//            Creando la llave para encriptar
            val masterKeyAlias =
                MasterKey.Builder(requireContext(), MasterKey.DEFAULT_MASTER_KEY_ALIAS)
                    .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                    .build()
            encryptedSharedPreferences = EncryptedSharedPreferences
                .create(
                    requireContext(),
                    "account",
                    masterKeyAlias,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
                ) as EncryptedSharedPreferences
        } catch (e: GeneralSecurityException) {
            e.printStackTrace()
            Log.d(Constants.LOGTAG_INFO, "Error: ${e.message}")
        } catch (e: IOException) {
            e.printStackTrace()
            Log.d(Constants.LOGTAG_INFO, "Error: ${e.message}")
        }
        encryptedSharedPrefsEditor = encryptedSharedPreferences.edit()
        if (!encryptedSharedPreferences.getString("userSp", "0").equals("0"))
            flagIdBiometricActived = true
        if (encryptedSharedPreferences.getString("userSp", "0").equals(user?.email)) {
            binding.mtrlbtnIdBiometric.text = "Desactivar ingreso con huella"
        } else {
            binding.mtrlbtnIdBiometric.visibility = View.VISIBLE
        }
        binding.mtrlbtnIdBiometric.setOnClickListener {
            if (flagEmailVerificated) {//Si el correo está verificado
                Toast.makeText(requireContext(), "email:$flagEmailVerificated bio:$flagIdBiometricActived bio_negado:${!flagIdBiometricActived}", Toast.LENGTH_LONG)
                    .show()
                if (!flagIdBiometricActived) {
                    AlertDialog.Builder(requireContext())
                        .setTitle("Aviso")
                        .setMessage("Con esta opción, activará el acceso con huella para la cuenta ${user?.email}")
                        .setPositiveButton(
                            "Aceptar",
                            DialogInterface.OnClickListener { dialog, which ->
                                encryptedSharedPrefsEditor.putString("userSp", user?.email)
//                                Toast.makeText(requireContext(), "PSW:$psw", Toast.LENGTH_SHORT)
//                                    .show()
                                encryptedSharedPrefsEditor.putString("pwdSp", psw)
                                encryptedSharedPrefsEditor.apply()
                                Toast.makeText(
                                    requireContext(),
                                    "Acceso con huella activado",
                                    Toast.LENGTH_SHORT
                                ).show()
                                binding.mtrlbtnIdBiometric.text = "Desactivar ingreso con huella"
                                flagIdBiometricActived = true
                            })
                        .setNegativeButton(
                            "Cancelar",
                            DialogInterface.OnClickListener { dialog, which ->
                                dialog.dismiss()
                            })
                        .create()
                        .show()
                } else {//Hay acceso con huella
                    AlertDialog.Builder(requireContext())
                        .setTitle("Aviso")
                        .setMessage("Con esta opción, desactivará el acceso con huella para la cuenta ${user?.email}")
                        .setPositiveButton(
                            "Aceptar",
                            DialogInterface.OnClickListener { dialog, which ->
                                encryptedSharedPrefsEditor.putString("userSp", "0")
                                encryptedSharedPrefsEditor.putString("pwdSp", "0")
                                encryptedSharedPrefsEditor.apply()
                                Toast.makeText(
                                    requireContext(),
                                    "Acceso con huella desactivado",
                                    Toast.LENGTH_SHORT
                                ).show()
                                binding.mtrlbtnIdBiometric.text = "Activar ingreso con huella"
                                flagIdBiometricActived = false
                            })
                        .setNegativeButton(
                            "Cancelar",
                            DialogInterface.OnClickListener { dialog, which ->
                                dialog.dismiss()
                            })
                        .create()
                        .show()
                }
            } else {  //del if banderaemailverificado
                AlertDialog.Builder(requireContext())
                    .setTitle("Aviso")
                    .setMessage("Se necesita verificar el correo electrónico primeramente para activar esta opción. Una vez verificado, por favor cierre sesión y vuelva a ingresar.")
                    .setPositiveButton("Aceptar", DialogInterface.OnClickListener { dialog, which ->
                        dialog.dismiss()
                    })
                    .create()
                    .show()
            }
        }

    }

    private fun setupMenu() {
        (requireActivity() as MenuHost).addMenuProvider(object : MenuProvider {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onPrepareMenu(menu: Menu) {
                customMenu(menu)
            }

            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.right_nav_user_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Validate and handle the selected menu item
                when (menuItem.itemId) {
                  /*  R.id.navigation_setting -> {
                        Toast.makeText(
                            requireContext(), "Settings",
                            Toast.LENGTH_LONG
                        ).show()
                    }*/
                    R.id.navigation_contact -> {
                        val parametros = Bundle().apply {
                            putInt("opcion",1)
                        }
                        findNavController().navigate(R.id.action_userFragment_to_generalInformationFragment,parametros)
//                        Toast.makeText(requireContext(), "contact", Toast.LENGTH_LONG).show()
                    }
                    R.id.navigation_about -> {
                        val parametros = Bundle().apply {
                            putInt("opcion",2)
                        }
                        findNavController().navigate(R.id.action_userFragment_to_generalInformationFragment,parametros)
//                        Toast.makeText(requireContext(), "about", Toast.LENGTH_LONG).show()
                    }
                }
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun customMenu(menu: Menu) {
        var drawableMore: Drawable = menu.findItem(R.id.navigation_more).icon!!
//        var drawableSettings: Drawable = menu.findItem(R.id.navigation_setting).icon!!
        var drawableContact: Drawable = menu.findItem(R.id.navigation_contact).icon!!
        var drawableAbout: Drawable = menu.findItem(R.id.navigation_about).icon!!
        //              Para saber si estoy en modo noche o no
        val mode = context?.resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)

        when (mode) {
            Configuration.UI_MODE_NIGHT_YES -> {
                drawableMore = DrawableCompat.wrap(drawableMore)
                DrawableCompat.setTint(
                    drawableMore, ContextCompat.getColor(
                        requireContext(),
                        R.color.md_theme_dark_onSurfaceVariant
                    )
                )
              /*  drawableSettings = DrawableCompat.wrap(drawableSettings)
                DrawableCompat.setTint(
                    drawableSettings, ContextCompat.getColor(
                        requireContext(),
                        R.color.md_theme_dark_onSurfaceVariant
                    )
                )*/
                drawableContact = DrawableCompat.wrap(drawableContact)
                DrawableCompat.setTint(
                    drawableContact, ContextCompat.getColor(
                        requireContext(),
                        R.color.md_theme_dark_onSurfaceVariant
                    )
                )
                drawableAbout = DrawableCompat.wrap(drawableAbout)
                DrawableCompat.setTint(
                    drawableAbout, ContextCompat.getColor(
                        requireContext(),
                        R.color.md_theme_dark_onSurfaceVariant
                    )
                )

            }
            Configuration.UI_MODE_NIGHT_NO -> {
                drawableMore = DrawableCompat.wrap(drawableMore)
                DrawableCompat.setTint(
                    drawableMore, ContextCompat.getColor(
                        requireContext(),
                        R.color.md_theme_light_onSurfaceVariant
                    )
                )
                /*drawableSettings = DrawableCompat.wrap(drawableSettings)
                DrawableCompat.setTint(
                    drawableSettings, ContextCompat.getColor(
                        requireContext(),
                        R.color.md_theme_light_onSurfaceVariant
                    )
                )*/
                drawableContact = DrawableCompat.wrap(drawableContact)
                DrawableCompat.setTint(
                    drawableContact, ContextCompat.getColor(
                        requireContext(),
                        R.color.md_theme_light_onSurfaceVariant
                    )
                )
                drawableAbout = DrawableCompat.wrap(drawableAbout)
                DrawableCompat.setTint(
                    drawableAbout, ContextCompat.getColor(
                        requireContext(),
                        R.color.md_theme_light_onSurfaceVariant
                    )
                )
            }
            Configuration.UI_MODE_NIGHT_UNDEFINED -> {

            }
        }

        /*for (i in 0 until menu.size()) {
                    val mi = menu.getItem(i)
                    val title = mi.title.toString()

//              Pintar soloel icono de afuera(los tres puntos del appbar)
                 *//*   mi.setIconTintList(
                            ColorStateList.valueOf(
                                ContextCompat.getColor(
                                    requireContext(),
                                    R.color.color_plate_yellow
                                )
                            )
                        )*//*
    //                    Pintar el primer elemento del submenu de los tres puntitos
                       *//* mi.subMenu!!.getItem(0).setIconTintList(
                            ColorStateList.valueOf(
                                ContextCompat.getColor(
                                    requireContext(),
                                    R.color.md_theme_light_onPrimaryContainer
                                )
                            )
                        )*//*
                    }*/
    }


}