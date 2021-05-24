import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Book } from '../model/book';
import { HttpClientService } from '../service/http-client.service';

@Component({
  selector: 'app-shopbook',
  templateUrl: './shopbook.component.html',
  styleUrls: ['./shopbook.component.scss']
})
export class ShopbookComponent implements OnInit {

  books: Array<Book> = new Array<Book>();
  booksRecieved: Array<Book> = Array<Book>();

  cartBooks: any;

  constructor(private router: Router, private httpClientService: HttpClientService) { }

  ngOnInit(): void {
    this.httpClientService.getBooks()
      .subscribe(response => this.handleSuccessfulResponse(response), );

    // from localstorage retrieve the cart item
    let data = localStorage.getItem('cart');
    // if thi is not null convert it to JSON else initialize it as empty
    if (data !== null) {
      this.cartBooks = JSON.parse(data);
    } else {
      this.cartBooks = [];
    }
  }

  // we will be taking the books response returned from the database and we will be adding the retrieved
  handleSuccessfulResponse(response: any) {
    this.books = new Array<Book>();
    // get books returnedby the api call
    this.booksRecieved = response;
    for (const book of this.booksRecieved) {
      const bookwithRetrivedImageField = new Book();
      bookwithRetrivedImageField.id = book.id;
      bookwithRetrivedImageField.name = book.name;
      // populate retrieved image field so book image can be displayed
      bookwithRetrivedImageField.retrivedImage = 'data:image/jpeg;base64,' + book.picByte;
      bookwithRetrivedImageField.author = book.author;
      bookwithRetrivedImageField.price = book.price;
      bookwithRetrivedImageField.picByte = book.picByte;
      this.books.push(bookwithRetrivedImageField);
    }
  }

  addToCart(bookId: number) {
    // retrieve book from books array using the boook bookId
    let book = this.books.find(item => item.id === bookId) || null;
    let cartData = [];
    // retrieve cart data from localStorage
    let data = localStorage.getItem('cart');
    // parse it to json
    if (data !== null) {
      cartData = JSON.parse(data);
    }
    // add the selected book to cart data
    cartData.push(book);
    // update the cartBook
    this.updateCartData(cartData);
    // save the updated cart data to the localstorage
    localStorage.setItem('cart', JSON.stringify(cartData));
    // make the isAdded field of the book added to cart as true
    if (book !== null) book.isAdded = true;
  }

  updateCartData(cartData: any) {
    this.cartBooks = cartData;
  }

  goToCart() {
    this.router.navigate(['/cart']);
  }

  emptyCart() {
    this.cartBooks = [];
    localStorage.clear();
  }
}
