package com.juanviana.practica1

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.juanviana.practica1.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*

private const val EMPTY = ""
private const val SPACE = " "

private lateinit var mainBinding: ActivityMainBinding
private var users: MutableList<User> = mutableListOf()
private var fechaNacimiento: String = ""
private var cal = Calendar.getInstance()

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, month, dayofMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, month)
            cal.set(Calendar.DAY_OF_MONTH, dayofMonth)

            val format = "MM/dd/yy"
            val sdf = SimpleDateFormat(format, Locale.US)
            fechaNacimiento = sdf.format(cal.time).toString()
            mainBinding.edBornDate.setText(fechaNacimiento)

        }
        mainBinding.edBornDate.setOnClickListener {
            DatePickerDialog(
                this, dateSetListener,
                cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)
            ).show()


        }


        mainBinding.button3.setOnClickListener {
            val email = mainBinding.EmailEditText.text.toString()
            var contra = mainBinding.ContraEditText.text.toString()
            val c_contra = mainBinding.confirEditText.text.toString()
            val genre =
                if (mainBinding.radioButtonFemale.isChecked) getString(R.string.Femenino) else getString(
                    R.string.Masculino
                )
            var hobbies = if (mainBinding.eatCheckbox.isChecked) getString(R.string.eat) else EMPTY
            hobbies =
                hobbies + SPACE + if (mainBinding.ReadCheckbox.isChecked) getString(R.string.leer) else EMPTY
            hobbies =
                hobbies + SPACE + if (mainBinding.SportCheckBox.isChecked) getString(R.string.Deporte) else EMPTY
            hobbies =
                hobbies + SPACE + if (mainBinding.playCheckbox.isChecked) getString(R.string.play) else EMPTY



            if (email.isNotEmpty() && mainBinding.edBornDate.text.toString()
                    .isNotEmpty() && contra.isNotEmpty()
            ) {
                if (mainBinding.playCheckbox.isChecked || mainBinding.SportCheckBox.isChecked ||
                    mainBinding.ReadCheckbox.isChecked || mainBinding.eatCheckbox.isChecked
                ) {

                    if (contra == c_contra) {
                        var place = mainBinding.bornSpinner.selectedItem.toString()
                        saveUser(email, contra, genre, hobbies, place, fechaNacimiento)
                        mainBinding.ConfirContraLayout.error = null
                    } else {
                        mainBinding.ConfirContraLayout.error = getString(R.string.password_error)
                    }

                } else {
                    Toast.makeText(this, getString(R.string.Email_required), Toast.LENGTH_LONG)
                        .show()
                }
            } else {

                Toast.makeText(this, getString(R.string.Email_required), Toast.LENGTH_LONG)
                    .show()
            }



            CleanView()


        }

    }

    private fun CleanView() {
        with(mainBinding) {
            EmailEditText.setText(EMPTY)
            ContraEditText.setText(EMPTY)
            confirEditText.setText(EMPTY)
            radioButtonFemale.isChecked = false
            radioButtonMale.isChecked = false
            eatCheckbox.isChecked = false
            ReadCheckbox.isChecked = false
            SportCheckBox.isChecked = false
            playCheckbox.isChecked = false
            edBornDate.setText(EMPTY)
        }
    }

    private fun saveUser(
        email: String,
        Password: String,
        genre: String,
        hobbies: String,
        lugar: String,
        fecha: String
    ) {
        val newUser = User(email, Password, genre, hobbies, lugar, fecha)
        users.add(newUser)
        printUserData()

    }

    private fun printUserData() {
        var info = ""
        for (user in users)
            info =
                info + "\n\n" + user.email + "\n\n" + user.hobbies + "\n\n" + user.genre + "\n\n" + user.password + "\n" +
                        "\n" + user.lugar + "\n" + user.fecha
        mainBinding.infoTextView.text = info
    }


}


