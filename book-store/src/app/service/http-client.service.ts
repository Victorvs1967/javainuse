import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { User } from '../model/user';
import { Observable } from 'rxjs';
import { Book } from '../model/book';

@Injectable({
  providedIn: 'root'
})
export class HttpClientService {

  constructor(private httpClient: HttpClient) { }

  getUsers(): Observable<User[]> {
    return this.httpClient.get<User[]>('http://localhost:8080/users/get');
  }

  addUser(user: User): Observable<User> {
    return this.httpClient.post<User>('http://localhost:8080/users/add', user);
  }

  deleteUser(id: number): Observable<User> {
    return this.httpClient.delete<User>('http://localhost:8080/users/' + id);
  }

  getBooks(): Observable<Book[]> {
    return this.httpClient.get<Book[]>('http://localhost:8080/books/get');
  }

  addBook(newBook: Book): Observable<Book> {
    return this.httpClient.post<Book>('http://localhost:8080/books/add', newBook);
  }

  deleteBook(id: number): Observable<Book> {
    return this.httpClient.delete<Book>('http://localhost:8080/books/' + id);
  }

  updateBook(updatedBook: Book): Observable<Book> {
    return this.httpClient.put<Book>('http://localhost:8080/books/update', updatedBook);
  }
}
