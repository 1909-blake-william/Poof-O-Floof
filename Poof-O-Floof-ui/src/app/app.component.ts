import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { LocationService } from './location.service';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

export class AppComponent implements OnInit {
  // ipGeolocComJSON: IpGeolocComJSON;
  title = 'Poof-O-Floof';
  latitude: number;
  longitude: number;

  constructor(
    private http: HttpClient,
    private locService: LocationService
  ) { }

  ngOnInit(): void {
    this.printCoords();
  }

  printCoords() {
    this.locService.getUserIpLocInfo().subscribe(
      (data) => {
        console.log(data);
      }
    );
  }

}


