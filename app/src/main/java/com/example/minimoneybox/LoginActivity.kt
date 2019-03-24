package com.example.minimoneybox

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.airbnb.lottie.LottieDrawable
import com.example.minimoneybox.util.LoginValidationUtil
import kotlinx.android.synthetic.main.activity_login.*

/**
 * A login screen that offers login via email/password.
 */
class LoginActivity: AppCompatActivity(), LoginPresenter.View {

    private val firstAnimation = 0 to 109
    private val secondAnimation = 131 to 158

    private lateinit var presenter: LoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setupViews()
        presenter = LoginPresenter(this)
    }

    override fun onStart() {
        super.onStart()
        setupAnimation()
    }

    override fun login(authToken: String, name: String?) {
        setLoadingState(false)
        if (!authToken.isBlank()) {
            val pref = getSharedPreferences("MY_PREFS", Context.MODE_PRIVATE).edit()
            pref.putString("AUTH_TOKEN_KEY", "Bearer $authToken").apply()
            if (name != null) pref.putString("USER_NAME_KEY", name).apply()
            startActivity(Intent(baseContext, UserAccountsActivity::class.java))
        }
    }

    override fun showLoginError() {
        setLoadingState(false)
        Toast.makeText(this, R.string.login_failed, Toast.LENGTH_LONG).show()
    }

    override fun setInputError(emailValid: Boolean, passwordValid: Boolean, nameValid: Boolean) {
        setLoadingState(false)
        if (!emailValid) til_email.error = getString(R.string.email_address_error)
        if (!passwordValid) til_password.error = getString(R.string.password_error)
        if (!nameValid && !et_name.text.toString().isBlank()) til_name.error = getString(R.string.full_name_error)
    }

    private fun setupViews() {
        btn_sign_in.setOnClickListener {
            setLoadingState(true)
            presenter.validateFields(
                LoginValidationUtil.LoginFieldsInput(
                    et_email.text.toString(),
                    et_password.text.toString(),
                    et_name.text.toString()
                )
            )
        }
    }

    private fun setLoadingState(isLoading: Boolean) {
        btn_sign_in.isEnabled = !isLoading
        login_loading_spinner.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun setupAnimation() {
        animation.addAnimatorUpdateListener {
            if (animation.frame == firstAnimation.second) {
                animation.setMinAndMaxFrame(secondAnimation.first, secondAnimation.second)
                animation.repeatCount = LottieDrawable.INFINITE
                animation.playAnimation()
            }
        }
        animation.playAnimation()
    }
}
