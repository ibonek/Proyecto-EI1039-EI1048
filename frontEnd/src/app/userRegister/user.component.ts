import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {UserService} from "../user.service";
import Swal from "sweetalert2";
import { getAuth, createUserWithEmailAndPassword } from "firebase/auth";
import { initializeApp } from "firebase/app";
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
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {

  email!: string
  password !:string;
  constructor(private route: ActivatedRoute,
              private router: Router,
              private userService: UserService) { }

  ngOnInit(): void {
  }

  register() {
    if (this.password.length < 6) {
      Swal.fire({
        title: 'Ooops',
        text: "Password must have at least 6 characters",
        icon: 'error',
        showCancelButton: false,
        confirmButtonColor: '#2ab2b9',
        confirmButtonText: 'Ok'
      })
      return;
    } else if (!this.email.includes('@') || !this.email.includes('.')) {
      Swal.fire({
        title: 'Ooops',
        text: "That email does not have the correct format (Format: abc@abc.abc)",
        icon: 'error',
        showCancelButton: false,
        confirmButtonColor: '#2ab2b9',
        confirmButtonText: 'Ok'
      })
      return;
    } else {
      createUserWithEmailAndPassword(auth, this.email, this.password)
        .then((userCredential) => {
          // Signed in
          const user = userCredential.user;
          if (user) {
            sessionStorage.setItem("email", this.email);
            window.location.reload();
            this.userService.register(this.email).subscribe(data => {
              console.log(data);
            });
          }
        })
        .catch(() => {
          Swal.fire({
            title: 'Ooops',
            text: "The email "+ this.email +" is already registered",
            icon: 'error',
            showCancelButton: false,
            confirmButtonColor: '#2ab2b9',
            confirmButtonText: 'Ok'
          })
        });
    }
  }
}
