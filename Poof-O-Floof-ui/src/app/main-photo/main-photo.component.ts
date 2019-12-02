import { Component, OnInit } from '@angular/core';
import { products } from '../products';
import { testPhotos } from '../testPhotos';

@Component({
  selector: 'app-main-photo',
  templateUrl: './main-photo.component.html',
  styleUrls: ['./main-photo.component.css']
})
export class MainPhotoComponent {
  Products = products;
  Photos = testPhotos;
}
