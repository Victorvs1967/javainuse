import { Component, Input, Output, OnInit, EventEmitter } from '@angular/core';
import { Router } from '@angular/router';
import { User } from 'src/app/model/user';
import { HttpClientService } from 'src/app/service/http-client.service';

@Component({
  selector: 'app-adduser',
  templateUrl: './adduser.component.html',
  styleUrls: ['./adduser.component.scss']
})
export class AdduserComponent implements OnInit {

  @Input()
  user: User = new User();
  @Output()
  userAddedEvent = new EventEmitter();

  newUser: User = new User();
  message?: string;
  password?: string;

  constructor(private httpClientService: HttpClientService, private router: Router) { }

  ngOnInit(): void {
    this.newUser = Object.assign({}, this.user);
  }

  addUser() {
    this.httpClientService.addUser(this.user)
      .subscribe(user => {
        this.userAddedEvent.emit();
        this.router.navigate(['admin', 'users']);
      });
  }

}
