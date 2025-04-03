import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../core/service/auth.service';
 
@Component({
  selector: 'app-register',
  imports: [CommonModule, RouterModule, FormsModule],
  templateUrl: './register.component.html',
})
export class RegisterComponent implements OnInit{
  firstName:string = ''
  lastName:string = ''
  username:string = ''
  password:string = ''
  isSubmitting:boolean = false
  validationErrors:any = []
  
  constructor(private router: Router, private authService: AuthService) {}
  
  ngOnInit(): void {
    if(sessionStorage.getItem('token') != "" && sessionStorage.getItem('token') != null){
      this.router.navigateByUrl('/movies')
    }
  }
  
  registerAction() {
    this.isSubmitting = true;
    let payload = {
      firstName:this.firstName,
      lastName:this.lastName,
      username:this.username,
      password:this.password
    }
  
    this.authService.register(payload).subscribe(
      (response) => {
        sessionStorage.setItem('token', response.token); 
        this.router.navigateByUrl('/movies'); 
        this.isSubmitting = false; 
      },
      (error) => {
        this.isSubmitting = false; 
        if (error.error && error.error.errors) {
          this.validationErrors = error.error.errors;
        } else {
          console.error(error);
        }
      }
    );
  }
}