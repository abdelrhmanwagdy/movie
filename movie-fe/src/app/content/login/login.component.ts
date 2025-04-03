import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../core/service/auth.service';
 
 
@Component({
  selector: 'app-login',
  imports: [CommonModule, RouterModule, FormsModule],
  templateUrl: './login.component.html',
})
export class LoginComponent implements OnInit{
  username:string = ''
  password:string = ''
  isSubmitting:boolean = false
  validationErrors:Array<any> = []
 
  constructor(private router: Router, private authService: AuthService) {}
 
  ngOnInit(): void {
    if(sessionStorage.getItem('token') != "" && sessionStorage.getItem('token') != null){
      this.router.navigateByUrl('/movies')
    }
  }
 
  loginAction() {
    this.isSubmitting = true;
    let payload = {
      username:this.username,
      password: this.password,
  }
  this.authService.login(payload).subscribe(
     (response) => {
        sessionStorage.setItem('token', response.token); 
        this.router.navigateByUrl('/movies');
        this.isSubmitting = false;
      },
      (error) => {
        this.isSubmitting = false;
        if (error.error && error.error.errors) {
          this.validationErrors = error.error.errors;
        } else if (error.error && error.error.error) {
          this.validationErrors = error.error.error;
        } else {
          console.error(error);
        }
      }
    );
  }
}