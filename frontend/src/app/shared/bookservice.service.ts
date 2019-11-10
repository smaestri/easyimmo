import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class Bookservice {

  constructor(private http : HttpClient) { }

  getMyBooks() {
    return this.http.get("/users/" +  sessionStorage.getItem('userId') + "/books");
  }

  deleteBook(idBook: number): any {
    return this.http.delete("/AD-SERVICE/users/1/books/" + idBook);
  }

  saveBook(book: any): any {
    return this.http.post("/AD-SERVICE/users/1/ad", book);
  }

  getBooksAvailable(): any {
    return this.http.get("/AD-SERVICE/users/1/ad");
  }

}
