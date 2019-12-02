import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule } from '@angular/router';
import { AppComponent } from './app.component';
import { HttpClientModule } from '@angular/common/http';
import { MainPhotoComponent } from './main-photo/main-photo.component';


@NgModule({
  imports: [
    HttpClientModule,
    BrowserModule,
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
