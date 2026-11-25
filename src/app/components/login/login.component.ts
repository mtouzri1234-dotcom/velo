import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './login.component.html',
})
export class LoginComponent {
  private readonly auth = inject(AuthService);
  private readonly router = inject(Router);
  username = '';
  password = '';
  error = false;

  signIn(): void {
    this.error = false;
    this.auth.signIn(this.username, this.password).subscribe({
      next: () => void this.router.navigate(['/bikes']),
      error: () => (this.error = true),
    });
  }
}