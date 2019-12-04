import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { RouterModule } from '@angular/router';
import { AppComponent } from './app.component';

import { MainPhotoComponent } from './main-photo/main-photo.component';


@NgModule({
  imports: [
    BrowserModule,
    HttpClientModule,
    RouterModule.forRoot([
      { path: '', component: MainPhotoComponent },
    ])
  ],
  declarations: [
    AppComponent,
    MainPhotoComponent
  ],
  bootstrap: [AppComponent],
  providers: []
})
export class AppModule { }
