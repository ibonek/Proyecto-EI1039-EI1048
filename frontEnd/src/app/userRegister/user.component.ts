import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {UserService} from "../user.service";
import Swal from "sweetalert2";
import { getAuth, createUserWithEmailAndPassword } from "firebase/auth";

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
      const auth = getAuth();
      createUserWithEmailAndPassword(auth, this.email, this.password)
        .then((userCredential) => {
          // Signed in
          const user = userCredential.user;
          if (user) {
            sessionStorage.setItem("email", this.email);
            Swal.fire({
              title: 'Welcome!',
              text: 'You are now registered in LookAPP!',
              icon: 'success',
              showCancelButton: false,
              confirmButtonColor: '#2ab2b9',
              confirmButtonText: 'Ok'
            }).then((result => {
              if (result.isConfirmed) {
                window.location.reload();
              }
            }))
            this.userService.register(this.email, this.password).subscribe(data => {
              console.log(data);
            });
          }
        })
        .catch((error) => {
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

  signIn() {
    if (!this.email.includes('@') || !this.email.includes('.')) {
      Swal.fire({
        title: 'Ooops',
        text: "That email does not have the correct format (Format: "
      });
    } else {
      this.userService.signIn(this.email, this.password).subscribe(data => {
        this.userService.getConfirmation().subscribe(confirmation => {
          if (confirmation) {
            sessionStorage.setItem("email", this.email);
          }
        })
      });
    }
  }
}
