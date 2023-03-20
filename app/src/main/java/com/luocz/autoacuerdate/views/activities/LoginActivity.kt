/*
 * **************************************************************************
 *   Author: Luis Octavio Goméz de la Cruz
 *   Date created: 02/02/23 12:15
 *   File: LoginActivity.kt
 *   Last modified: 02/02/23 12:13
 *   Last change:
 *     Change Name ->
 *     Change Date -> 02/02/23 12:15
 *     Description ->
 *
 * **************************************************************************
 */

package com.luocz.autoacuerdate.views.activities

import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.luocz.autoacuerdate.R
import com.luocz.autoacuerdate.databinding.ActivityLoginBinding
import com.luocz.autoacuerdate.databinding.FragmentMapBinding
import com.luocz.autoacuerdate.utils.Constants
import java.io.IOException
import java.security.GeneralSecurityException
import java.util.concurrent.Executor

class LoginActivity : AppCompatActivity() {
    private lateinit var  binding: ActivityLoginBinding
    //    Flag Id Biometric si el disp. cuenta con lector
    private var flagIDBiometric = true
    //Si se entrara con huella
    private var flagLogWithIdBiometric = false
    //para los mensajes de error
    private var textErrorIdBiometric = ""
    private lateinit var biometricManager: BiometricManager
    private lateinit var executor: Executor
    //Shared Preferences encryptadas
    private lateinit var  encryptedSharedPreferences: EncryptedSharedPreferences
    private lateinit var  encryptedSharedPrefEditor: SharedPreferences.Editor
    //    Shared preferences
    private var userSp: String? = ""
    private var pwdSp: String? = ""
//    Firebase
    private lateinit var  firebaseAuth: FirebaseAuth
//    user
    private var email = ""
    private var contrasenia = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        val screenSplash = installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Thread.sleep(3000)
        screenSplash.setKeepOnScreenCondition { false }
        firebaseAuth = FirebaseAuth.getInstance()

        try {
//            Creando una llave para encryptar
            val masterKeyAlias = MasterKey.Builder(this, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build()
            encryptedSharedPreferences = EncryptedSharedPreferences
                .create(this,
                "account",
                masterKeyAlias,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM) as EncryptedSharedPreferences

        }catch(e: GeneralSecurityException){
            e.printStackTrace()
            Log.d(Constants.LOGTAG_INFO, "Error: ${e.message}")
        }catch (e: IOException){
            e.printStackTrace()
            Log.d(Constants.LOGTAG_INFO, "Error: ${e.message}")
        }

        encryptedSharedPrefEditor = encryptedSharedPreferences.edit()
        userSp = encryptedSharedPreferences.getString("userSp","0")
        pwdSp = encryptedSharedPreferences.getString("pwdSp","0")

        biometricManager = BiometricManager.from(this)
        executor = ContextCompat.getMainExecutor(this)
        when(biometricManager.canAuthenticate(BiometricManager.Authenticators.DEVICE_CREDENTIAL)){
            BiometricManager.BIOMETRIC_SUCCESS -> {
                Log.d(Constants.LOGTAG_INFO, "La aplicaciòn puede autenticar usando biometria")
            }
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                flagIDBiometric = false
                textErrorIdBiometric = "El dispositivo no cuenta con lector de huella digital"
            }
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                flagIDBiometric = false
                textErrorIdBiometric = "El lector de huella no está disponible actualmente"
            }
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                flagIDBiometric = false
                textErrorIdBiometric = "Favor de asociar una huella digital al dispositivo primeramente"
            }
        }


        binding.ibtnHuella.setOnClickListener {
            if (userSp == "0"){
                Toast.makeText(applicationContext, "Ningun usuario activo para ingresar por medio de huella digital", Toast.LENGTH_SHORT).show()
            }else{
                showBiometricPrompt()
            }
        }

        binding.btnLogin.setOnClickListener {
            if(!validaCampos()) return@setOnClickListener
            binding.progressBar.visibility = View.VISIBLE
            autenticaUsuario(email, contrasenia)
        }

        binding.btnRegistrarse.setOnClickListener {
            if(!validaCampos()) return@setOnClickListener

            binding.progressBar.visibility = View.VISIBLE
//            Registrar el usuario
            firebaseAuth.createUserWithEmailAndPassword(email,contrasenia).addOnCompleteListener {
                    authResult ->
                if(authResult.isSuccessful){
//              Se enviara un correo de confirmación
                    var user_firebase = firebaseAuth.currentUser
                    user_firebase?.sendEmailVerification()?.addOnSuccessListener {
                        Toast.makeText(this, resources.getString(R.string.email_send),Toast.LENGTH_SHORT).show()
                    }?.addOnFailureListener {
                        Toast.makeText(this, resources.getString(R.string.fail_email_send),Toast.LENGTH_SHORT).show()
                    }
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }else{
                    binding.progressBar.visibility = View.GONE
                    manejaErrores(authResult)
                }
            }
        }
        binding.tvRestablecerPassword.setOnClickListener {
            val resetEmail = EditText(it.context)
            resetEmail.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
            val passwordResetDialog = AlertDialog.Builder(it.context)
            passwordResetDialog.setTitle(resources.getString(R.string.reset_password))
            passwordResetDialog.setMessage(resources.getString(R.string.enter_reset_password))
            passwordResetDialog.setView(resetEmail)
            passwordResetDialog.setPositiveButton(resources.getString(R.string.send),{
                dialog,which ->
                val email = resetEmail.text.toString()
                if(email.isNotEmpty()){
                    firebaseAuth.sendPasswordResetEmail(email).addOnSuccessListener {
                        Toast.makeText(this, resources.getString(R.string.send_mail_reset),Toast.LENGTH_SHORT).show()
                    }?.addOnFailureListener {
                        Toast.makeText(this, resources.getString(R.string.fail_send_mail_reset),Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(this, resources.getString(R.string.warning_send_mail_reset),Toast.LENGTH_SHORT).show()
                }
            })
            passwordResetDialog.setNegativeButton(resources.getString(R.string.request_permission_cancel),{
                dialog, which ->
                dialog.dismiss()
            })
            passwordResetDialog.create().show()
        }
    }

    private fun showBiometricPrompt() {
        if (biometricManager.canAuthenticate(BiometricManager.Authenticators.DEVICE_CREDENTIAL) != BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE){
            val promptInfo = BiometricPrompt.PromptInfo.Builder()
                .setTitle("Authentication")
                .setSubtitle("Log in use your id biometric")
                .setNegativeButtonText("Cancel")
                .build()
            val biometricPrompt = BiometricPrompt(this, executor, object : BiometricPrompt.AuthenticationCallback(){
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)

                    if(flagIDBiometric){
                        Toast.makeText(applicationContext, "No se pudo autenticar", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(applicationContext, textErrorIdBiometric, Toast.LENGTH_SHORT).show()
                    }

                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    val authenticatedCryptoObject = result.cryptoObject
                    //Autenticación exitosa
                    binding.progressBar.visibility = View.VISIBLE
                    if (userSp!=null && pwdSp != null)
                        autenticaUsuario(userSp!!,pwdSp!!)
                    flagLogWithIdBiometric = true
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Toast.makeText(applicationContext, "Autenticación fallida, no s epudo reconocer", Toast.LENGTH_SHORT).show()
                }
            })

            //Desplegando el biometric prompt
            biometricPrompt.authenticate(promptInfo)

        }else{
            Toast.makeText(applicationContext, textErrorIdBiometric, Toast.LENGTH_SHORT).show()
        }
    }

    private fun validaCampos(): Boolean{
        email = binding.tietEmail.text.toString().trim()
        contrasenia = binding.tietContrasenia.text.toString().trim()

        if(email.isEmpty()){
            binding.tietEmail.error = resources.getString(R.string.warning_send_mail_reset)
            binding.tietEmail.requestFocus()
            return false
        }

        if(contrasenia.isEmpty() || contrasenia.length < 6){
            binding.tietContrasenia.error =  resources.getString(R.string.invalid_password)
            binding.tietContrasenia.requestFocus()
            return false
        }

        return true
    }

    private fun manejaErrores(task: Task<AuthResult>){
        var errorCode = ""

        try{
            errorCode = (task.exception as FirebaseAuthException).errorCode
        }catch(e: Exception){
            errorCode = "NO_NETWORK"
        }

        when(errorCode){
            "ERROR_INVALID_EMAIL" -> {
                Toast.makeText(this, getString(R.string.error_invalid_email), Toast.LENGTH_SHORT).show()
                binding.tietEmail.error = getString(R.string.error_invalid_email)
                binding.tietEmail.requestFocus()
            }
            "ERROR_WRONG_PASSWORD" -> {
                Toast.makeText(this, getString(R.string.error_wrong_password), Toast.LENGTH_SHORT).show()
                binding.tietContrasenia.error = getString(R.string.error_wrong_password)
                binding.tietContrasenia.requestFocus()
                binding.tietContrasenia.setText("")
                if(flagLogWithIdBiometric){
                    //Quitamos al usuario que estaba activo
                    encryptedSharedPrefEditor.putString("userSp","0")
                    encryptedSharedPrefEditor.putString("pwdSp","0")
                    userSp = "0"

                    flagLogWithIdBiometric = false
                    AlertDialog.Builder(this)
                        .setTitle("Aviso")
                        .setMessage("La contraseña del usuario almacenado con huella activa ha cambiado. Favor de iniciar sesión con su correo electrónico y activar nuevamente el ingreso con huella.")
                        .setPositiveButton("Aceptar", DialogInterface.OnClickListener { dialog, which ->
                            dialog.dismiss()
                        })
                        .create()
                        .show()
                }
            }
            "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL" -> {
                //An account already exists with the same email address but different sign-in credentials. Sign in using a provider associated with this email address.
                Toast.makeText(this, getString(R.string.error_account_exists_with_diff_credentials), Toast.LENGTH_SHORT).show()
            }
            "ERROR_EMAIL_ALREADY_IN_USE" -> {
                Toast.makeText(this, getString(R.string.error_email_already_in_use), Toast.LENGTH_LONG).show()
                binding.tietEmail.error = getString(R.string.error_email_already_in_use)
                binding.tietEmail.requestFocus()
            }
            "ERROR_USER_TOKEN_EXPIRED" -> {
                Toast.makeText(this, getString(R.string.error_user_token_expired), Toast.LENGTH_LONG).show()
            }
            "ERROR_USER_NOT_FOUND" -> {
                Toast.makeText(this, getString(R.string.error_user_not_found), Toast.LENGTH_LONG).show()
            }
            "ERROR_WEAK_PASSWORD" -> {
                Toast.makeText(this, getString(R.string.error_weak_password), Toast.LENGTH_LONG).show()
                binding.tietContrasenia.error = getString(R.string.error_weak_password)
                binding.tietContrasenia.requestFocus()
            }
            "NO_NETWORK" -> {
                Toast.makeText(this, getString(R.string.not_network), Toast.LENGTH_LONG).show()
            }
            else -> {
                Toast.makeText(this, getString(R.string.error_unknown), Toast.LENGTH_SHORT).show()
            }
        }

    }
    private fun autenticaUsuario(usr: String, psw: String){
        firebaseAuth.signInWithEmailAndPassword(usr, psw).addOnCompleteListener { authResult ->
            if(authResult.isSuccessful){
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("psw",psw)
                startActivity(intent)
                finish()

            }else{
                binding.progressBar.visibility = View.GONE
                manejaErrores(authResult)
            }
        }
    }
}