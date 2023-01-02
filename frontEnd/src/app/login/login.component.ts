import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {UserService} from "../user.service";
import { getAuth, signInWithEmailAndPassword } from "firebase/auth";
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
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  email!: string
  password !:string;
  constructor(private route: ActivatedRoute,
              private router: Router,
              private userService: UserService) { }

  ngOnInit(): void {
    if (sessionStorage.getItem("email")) {
      this.router.navigate(['/events']);
    }
  }

  login() {
    if (!this.email.includes('@') || !this.email.includes('.')) {
      Swal.fire({
        title: 'Ooops',
        text: "That email does not have the correct format (Format: "
      });
    } else {
      signInWithEmailAndPassword(auth, this.email, this.password)
        .then((userCredential) => {
          // Signed in
          const user = userCredential.user;
          if (user) {
            sessionStorage.setItem("email", this.email);
            this.userService.signIn(this.email).subscribe(data => {
              window.location.reload();
            });
          }
        })
        .catch(() => {
          Swal.fire({
            title: 'Ooops',
            text: "Wrong email or password",
            icon: 'error',
            showCancelButton: false,
            confirmButtonColor: '#2ab2b9',
            confirmButtonText: 'Ok'
          })
        });
    }
  }
}
