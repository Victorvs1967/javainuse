import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Book } from 'src/app/model/book';
import { HttpClientService } from 'src/app/service/http-client.service';

@Component({
  selector: 'app-books',
  templateUrl: './books.component.html',
  styleUrls: ['./books.component.scss']
})
export class BooksComponent implements OnInit {

  books: Array<Book> = Array<Book>();
  booksRecieved: Array<Book> = Array<Book>();
  selectedBook: Book = new Book();
  action?: string;

  constructor(private httpClientService: HttpClientService, private router: Router, private activatedRoute: ActivatedRoute) { }

  ngOnInit(): void {
    this.refreshData();
  }

  // we will be taking the boosk response returned from the database and we will be adding the retrieved
  handleSuccessfulResponse(response: Book[]) {
    this.books = new Array<Book>();
    // get books returned by the api call
    this.booksRecieved = response;
    for (const book of this.booksRecieved) {
      const bookwithRetrievedImageField = new Book();
      bookwithRetrievedImageField.id = book.id;
      bookwithRetrievedImageField.name = book.name;
      // populate retrieved image field so that book image can be displayed
      bookwithRetrievedImageField.retrivedImage = 'data:image/jpeg;base64,' + book.picByte;
      bookwithRetrievedImageField.author = book.author;
      bookwithRetrievedImageField.price = book.price;
      bookwithRetrievedImageField.picByte = book.picByte;
      this.books.push(bookwithRetrievedImageField);
    }
  }

  addBook() {
    this.router.navigate(['admin', 'books'], { queryParams: { action: 'add' } });
  }

  refreshData() {
    this.httpClientService.getBooks()
      .subscribe(response => this.handleSuccessfulResponse(response));

    this.activatedRoute.queryParams
      .subscribe(params => {
        // get url parameter named action, this can either be view or add.  
        this.action = params['action'];
        // get the parameter id. this will be the id of the book whose details are to be displayed when action is view.
        const id = params['id'];
        // if id exists, convert it to integer and then retrive the book from the books array
        if (id) {
          this.selectedBook = this.books.find(book => book.id === +id) as Book;
        }
    });
  }

  viewBook(id: number) {
    this.router.navigate(['admin', 'books'], { queryParams: { id, action: 'view' } });
  }

}
