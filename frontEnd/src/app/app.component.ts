import {Component, Input, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{

  title: string;

  constructor(private route: ActivatedRoute,
              private router: Router) {
    this.title = 'Spring Boot - Angular Application';
  }

  ngOnInit(): void {
    if (sessionStorage.getItem("email")==null){
      this.router.navigate(['/register']);    }
  }

}
