import { Component, OnInit } from '@angular/core';
import { getAuth, sendPasswordResetEmail } from "firebase/auth";
import { initializeApp } from "firebase/app";
import Swal from 'sweetalert2';
const firebaseConfig = {
  apiKey: "AIzaSyDrfr90AsmMD6MjooOQ42eTEfxrUTYE9Fo",
  authDomain: "proyectoei1039-1048.firebaseapp.com",
  databaseURL: "https://proyectoei1039-1048-default-rtdb.firebaseio.com",
  projectId: "proyectoei1039-1048",
  storageBucket: "proyectoei1039-1048.appspot.com",
  messagingSenderId: "462858620301",
  appId: "1:462858620301:web:143a2137db1ffd69c9c1c4",
  measurementId: "G-B186TDEPPE"
}
const app = initializeApp(firebaseConfig);
const auth = getAuth(app);
@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.css']
})
export class ResetPasswordComponent implements OnInit {
  email!: string
  constructor() { }

  ngOnInit(): void {
  }

  resetPassword() {
    sendPasswordResetEmail(auth, this.email)
      .then(() => {
        Swal.fire({
          title: 'Success',
          text: "An email has been sent to you with the instructions to reset your password",
          icon: 'success',
          showCancelButton: false,
          confirmButtonColor: '#2ab2b9',
          confirmButtonText: 'Ok'
        })
      })
      .catch(() => {
        Swal.fire({
          title: 'Ooops',
          text: "There was an error sending the email",
          icon: 'error',
          showCancelButton: false,
          confirmButtonColor: '#2ab2b9',
          confirmButtonText: 'Ok'
        })
      });
  }
}
