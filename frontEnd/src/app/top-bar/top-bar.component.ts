import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {UserService} from "../user.service";
import Swal from "sweetalert2";
import { getAuth, deleteUser } from "firebase/auth";
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
  selector: 'app-top-bar',
  templateUrl: './top-bar.component.html',
  styleUrls: ['./top-bar.component.css']
})
export class TopBarComponent implements OnInit {

  email!:string | null;
  constructor(private route: ActivatedRoute,
              private router: Router,
              private userService: UserService) { }

  ngOnInit(): void {
    this.email=sessionStorage.getItem("email");
  }

  goLocList(){
    this.router.navigate(['/locationList']);
  }

  goEventList(){
    this.router.navigate(['/events']);
  }

  goAddLoc(){
    this.router.navigate(['/addLocation']);
  }
  goApis(){
    this.router.navigate(['/apisList']);
  }

  signOut(){
    Swal.fire({
      title: 'Do you want to sign out?',
      text: 'All your changes have been saved',
      icon: 'question',
      confirmButtonColor: '#2ab2b9',
      showCancelButton: true,
      confirmButtonText: 'Yes',
      cancelButtonText: 'No',
      cancelButtonColor: '#ff0421',
      reverseButtons: true
    }).then(( result => {
      if (result.isConfirmed) {
        this.userService.signOut(this.email).subscribe(data => {
          sessionStorage.removeItem("email");
          this.email=null;
          window.location.reload();
        })
      }
    }))
  }

  deleteAccount() {
    Swal.fire({
      title: 'Do you want to delete your account?',
      text: 'All your changes have been saved',
      icon: 'question',
      confirmButtonColor: '#2ab2b9',
      showCancelButton: true,
      confirmButtonText: 'Yes',
      cancelButtonText: 'No',
      cancelButtonColor: '#ff0421',
      reverseButtons: true
    }).then(( result => {
      if (result.isConfirmed) {
        deleteUser(auth.currentUser!).then(() => {
          this.userService.deleteAccount(this.email).subscribe(data => {
            sessionStorage.removeItem("email");
            this.email=null;
            window.location.reload();
          })
        }).catch((error) => {
          console.log(error);
        }
        );
      }
    }
    ))
  }
}
