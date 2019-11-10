import { Component } from '@angular/core';
import { Bookservice } from '../shared/bookservice.service';

@Component({
  selector: 'list-ad',
  templateUrl: './listad.component.html',
})
export class ListAdComponent {
  title = 'sharebook-frontend';
  ads;


  constructor (private bookService : Bookservice) {

  }

  ngOnInit() {
    this.ads = [];
    this.bookService.getBooksAvailable().subscribe((data) => {
      this.ads = data;
    }, () => {
      alert('error detected')
    });
  }

  
}
