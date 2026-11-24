import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { User } from '../model/user.model';

type Role = 'ADMIN' | 'USER';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private readonly users: User[] = [
    { username: 'admin', password: '123', roles: ['ADMIN'] },
    { username: 'youssef', password: '123', roles: ['USER'] },
  ];

  private currentRoles: Role[] | null = null;
  username: string | null = null;

  constructor(private readonly router: Router) {
    const savedUsername = localStorage.getItem('bike-user');
    const savedUser = this.users.find((user) => user.username === savedUsername);
    if (savedUser) {
      this.username = savedUser.username;
      this.currentRoles = savedUser.roles;
    }
  }

  isLoggedIn(): boolean {
    return this.username !== null;
  }

  isAdmin(): boolean {
    return this.currentRoles?.includes('ADMIN') ?? false;
  }

  signIn(username: string, password: string): boolean {
    const user = this.users.find(
      (candidate) => candidate.username === username && candidate.password === password,
    );
    if (!user) return false;

    this.username = user.username;
    this.currentRoles = user.roles;
    localStorage.setItem('bike-user', user.username);
    return true;
  }

  logout(): void {
    this.username = null;
    this.currentRoles = null;
    localStorage.removeItem('bike-user');
    void this.router.navigate(['/login']);
  }
}