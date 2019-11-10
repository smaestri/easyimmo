import { Component } from '@angular/core';
import { Bookservice } from '../shared/bookservice.service';
import { Router} from '@angular/router';

@Component({
  selector: 'newad',
  templateUrl: './newad.component.html',
  styleUrls: ['./newad.component.scss']
})
export class NewAdComponent {
  
  ad = {
    title: '',
    description: ''
  }

  constructor(private bookService : Bookservice, private router: Router) { }

  save() {
    this.bookService.saveBook(this.ad).subscribe(() => {
      this.router.navigate(['']);
    }, () => alert('erreur'))
  }

}
