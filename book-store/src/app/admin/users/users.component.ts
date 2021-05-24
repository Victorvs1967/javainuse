import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from 'src/app/model/user';
import { HttpClientService } from 'src/app/service/http-client.service';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.scss']
})
export class UsersComponent implements OnInit {

  users: Array<User> = new Array<User>();
  selectedUser: User = new User();
  action?: string;

  constructor(private httpClientService: HttpClientService, private activatedRoute: ActivatedRoute, private router: Router) { }

  ngOnInit(): void {
    this.refreshData();
  }

  handleSuccessfulResponse(response: User[]) {
    this.users = response;
  }

  addUser() {
    this.router.navigate(['admin', 'users'], { queryParams: {action: 'add'} });
  }

  viewUser(id: number) {
    this.router.navigate(['admin', 'users'], { queryParams: { id, action: 'view' }});
  }

  refreshData() {
    try {
      this.httpClientService.getUsers()
        .subscribe(data => this.handleSuccessfulResponse(data));
  
      this.activatedRoute.queryParams
        .subscribe(params => {
          this.action = params["action"];
          const selectedUserId = params["id"];
          if (selectedUserId) {
            this.selectedUser = this.users.find(user => user.id === +selectedUserId) as User;
          }
        });
    } catch {
      console.error('User table empty.');
    }
  }

}
