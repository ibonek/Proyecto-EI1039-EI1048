import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-top-bar',
  templateUrl: './top-bar.component.html',
  styleUrls: ['./top-bar.component.css']
})
export class TopBarComponent implements OnInit {

  email!:string | null;
  constructor() { }

  ngOnInit(): void {
    this.email=sessionStorage.getItem("email");
  }

}
