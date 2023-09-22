import { Component, ViewChild, OnInit, AfterViewInit, ElementRef } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';

import { LoginService } from 'app/login/login.service';
import { AccountService } from 'app/core/auth/account.service';
import { RegisterService } from 'app/account/register/register.service';
import { UserService } from 'app/user/user.service';

@Component({
  selector: 'jhi-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent implements OnInit, AfterViewInit {
  @ViewChild('username', { static: false })
  username!: ElementRef;
  success = false;
  authenticationError = false;

  loginForm = new FormGroup({
    username: new FormControl('', { nonNullable: true, validators: [Validators.required] }),
    password: new FormControl('', { nonNullable: true, validators: [Validators.required] }),
    rememberMe: new FormControl(false, { nonNullable: true, validators: [Validators.required] }),
  });

  constructor(
    private accountService: AccountService,
    private loginService: LoginService,
    private router: Router,
    private registerService: RegisterService,
    private userService: UserService
  ) {}

  // function: void(){

  // }

  ngOnInit(): void {
    // if already authenticated then navigate to home page
    // if (this.registerService.success === true) {
    //   this.success = true;
    //   wait;
    // }
    this.successRegister();
    // this.accountService.identity().subscribe(() => {
    //   if (this.accountService.isAuthenticated()) {
    //     this.router.navigate(['']);
    //   }
    // });
  }

  ngAfterViewInit(): void {
    this.username.nativeElement.focus();
  }

  login(): void {
    this.loginService.login(this.loginForm.getRawValue()).subscribe({
      next: () => {
        this.authenticationError = false;
        if (!this.router.getCurrentNavigation()) {
          // There were no routing during login (eg from navigationToStoredUrl)
          console.log(this.accountService.userIdentity?.levelOfForm);
          this.accountService.userIdentity?.levelOfForm === 4 ? this.router.navigate(['/']) : this.router.navigate(['/user/profile1']);
          // this.userService.user = this.accountService.getUser();
        }
      },
      error: () => (this.authenticationError = true),
    });
  }
  delay(ms: number): Promise<void> {
    return new Promise(resolve => setTimeout(resolve, ms));
  }

  async successRegister(): Promise<void> {
    if (this.registerService.success === true) {
      this.success = true;
      // eslint-disable-next-line no-console
      console.log('test');

      await this.delay(5000);
      this.success = false;
      // eslint-disable-next-line no-console
      console.log('test');
    }
  }
}
