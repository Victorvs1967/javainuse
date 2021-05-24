import { HttpClient } from '@angular/common/http';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Book } from 'src/app/model/book';
import { HttpClientService } from 'src/app/service/http-client.service';

@Component({
  selector: 'app-addbook',
  templateUrl: './addbook.component.html',
  styleUrls: ['./addbook.component.scss']
})
export class AddbookComponent implements OnInit {

  @Input()
  book: Book = new Book();

  @Output()
  bookAddedEvent = new EventEmitter();

  imgUrl: any;
  private selectedFile: any;

  constructor(private httpClientService: HttpClientService, private activatedRoute: ActivatedRoute,
              private router: Router, private httpClient: HttpClient) { }

  ngOnInit(): void {
  }

  public onFileChanged(event: any) {
    this.selectedFile = event.target.files[0];

    let reader = new FileReader();
    reader.readAsDataURL(event.target.files[0]);
    reader.onload = e => this.imgUrl = reader.result;
  };

  saveBook() {
    // If there is no book id then it is ab add book call else it is an edit book call
    if (this.book.id == null) {
      const uploadData = new FormData();
      uploadData.append('imageFile', this.selectedFile, this.selectedFile.name);
      this.selectedFile.imageName = this.selectedFile.name;
  
      this.httpClient.post('http://localhost:8080/books/upload', uploadData, { observe: 'response' })
        .subscribe(response => {
          if (response.status === 200) {
            this.httpClientService.addBook(this.book)
              .subscribe(book => {
                this.bookAddedEvent.emit();
                this.router.navigate(['admin', 'books']);
              });
            console.log('Image upload successfully');
          } else {
            console.log('Image not upload successfully');
          }
        })
    } else {
      this.httpClientService.updateBook(this.book)
        .subscribe(book => {
          this.bookAddedEvent.emit();
          this.router.navigate(['admin', 'books']);
        });
    }
  }

}
