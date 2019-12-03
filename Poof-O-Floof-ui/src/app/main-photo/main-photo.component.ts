import { Component, OnInit } from '@angular/core';
import { testPhotos } from '../testPhotos';
import { ConditionalExpr } from '@angular/compiler';

@Component({
  selector: 'app-main-photo',
  templateUrl: './main-photo.component.html',
  styleUrls: ['./main-photo.component.css']
})

export class MainPhotoComponent implements OnInit {
  Photos = testPhotos;
  photoNum = this.Photos.length;
  photoUrl: string;

  getNewPhotoUrl() {
    const rndPhotoNum = Math.round(Math.random() * (this.photoNum - 1));
    const photoLargeUrl = this.Photos[rndPhotoNum].large;
    this.photoUrl = photoLargeUrl;
  }

  ngOnInit() {
    this.getNewPhotoUrl();
  }

}
