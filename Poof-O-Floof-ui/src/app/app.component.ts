import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { LocationService } from './services/location.service';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

export class AppComponent implements OnInit {
  title = 'Poof-O-Floof';
  latitude: number;
  longitude: number;

  constructor(
    private http: HttpClient,
    private locService: LocationService
  ) { }

  ngOnInit(): void { }

  printCoords() {
    this.locService.getUserIpLocInfo().subscribe(
      (data) => {
        console.log(data);
      }
    );
  }

}
