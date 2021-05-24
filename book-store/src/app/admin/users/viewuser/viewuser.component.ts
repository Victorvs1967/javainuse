import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Router } from '@angular/router';
import { User } from 'src/app/model/user';
import { HttpClientService } from 'src/app/service/http-client.service';

@Component({
  selector: 'app-viewuser',
  templateUrl: './viewuser.component.html',
  styleUrls: ['./viewuser.component.scss']
})
export class ViewuserComponent implements OnInit {

  @Input()
  user: User = new User();

  @Output()
  userDeletedEvent = new EventEmitter();

  constructor(private httpClientService: HttpClientService, private router: Router) { }

  ngOnInit(): void {
  }

  deleteUser() {
    if (this.user.id) {
      this.httpClientService.deleteUser(this.user.id)
        .subscribe(user => {
          this.user = user;
          this.userDeletedEvent.emit();
          this.router.navigate(['admin', 'users']);
        });
    }
  }

}
