import {Component, Input, OnInit, ViewChild} from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{

  title: string;

  constructor() {
    this.title = 'Spring Boot - Angular Application';
  }

  ngOnInit(): void {
  }

}
