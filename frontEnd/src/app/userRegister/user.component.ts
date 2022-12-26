import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {FindingByNameService} from "../finding-by-name.service";
import {UserService} from "../user.service";
import {InformationService} from "../Infomation/information.service";
import Swal from "sweetalert2";
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

  register(){
    this.userService.register(this.email,this.password).subscribe(data => {
      this.userService.getConfirmation().subscribe(confirmation => {
        if (confirmation){
          sessionStorage.setItem("email",this.email);
          Swal.fire({
            title: 'Welcome!',
            text: 'You are now registered in NOMBREAPP',
            icon: 'success',
            showCancelButton: false,
            confirmButtonColor: '#c2185b',
            confirmButtonText: 'Ok'
          }).then(( result => {
            if (result.isConfirmed) {
              this.router.navigate(['/']);
            }
          }))
        }else{
          Swal.fire({
            title: 'Ooops',
            text: "You can't register with that email or password",
            icon: 'error',
            showCancelButton: false,
            confirmButtonColor: '#c2185b',
            confirmButtonText: 'Ok'
          })
        }

      })
    });
  }
}
