import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable, tap } from 'rxjs';
import { environment } from '../../environments/environment';

type Role = 'ADMIN' | 'USER';

interface LoginResponse {
  username: string;
  roles: Role[];
}

@Injectable({ providedIn: 'root' })
export class AuthService {
  private readonly http = inject(HttpClient);

  private currentRoles: Role[] | null = null;
  username: string | null = null;

  constructor(private readonly router: Router) {
    const savedUsername = localStorage.getItem('bike-user');
    const savedRoles = localStorage.getItem('bike-roles');
    if (savedUsername && savedRoles) {
      this.username = savedUsername;
      this.currentRoles = JSON.parse(savedRoles) as Role[];
    }
  }

  isLoggedIn(): boolean {
    return this.username !== null;
  }

  isAdmin(): boolean {
    return this.currentRoles?.includes('ADMIN') ?? false;
  }

  signIn(username: string, password: string): Observable<LoginResponse> {
    return this.http
      .post<LoginResponse>(`${environment.apiUrl}/auth/login`, { username, password })
      .pipe(tap((login) => {
        this.username = login.username;
        this.currentRoles = login.roles;
        localStorage.setItem('bike-user', login.username);
        localStorage.setItem('bike-roles', JSON.stringify(login.roles));
      }));
  }

  logout(): void {
    this.username = null;
    this.currentRoles = null;
    localStorage.removeItem('bike-user');
    localStorage.removeItem('bike-roles');
    void this.router.navigate(['/login']);
  }
}