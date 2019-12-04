import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

export class AppComponent {
  title = 'Poof-O-Floof';
  latitude: number;
  longitude: number;

  getLocationByPermission(): void {
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition((position) => {
        this.latitude = position.coords.latitude;
        this.longitude = position.coords.longitude;
      });
    } else {
      console.log('No support for geolocation');
    }
  }

  printLocation() {
    if (!this.latitude || !this.longitude) {
      console.log('location is not defined');
    } else {
      console.log(`${this.latitude},${this.longitude}`);
    }
  }

}

