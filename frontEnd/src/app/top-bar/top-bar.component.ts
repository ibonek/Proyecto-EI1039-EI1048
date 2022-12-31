import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {UserService} from "../user.service";
import Swal from "sweetalert2";

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

}
