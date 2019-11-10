import { Component } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { Bookservice } from './shared/bookservice.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'sharebook-frontend';

}
