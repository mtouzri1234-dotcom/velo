import { TestBed } from '@angular/core/testing';
import { provideRouter } from '@angular/router';
import { AuthService } from './auth.service';

describe('AuthService', () => {
  let auth: AuthService;

  beforeEach(() => {
    localStorage.clear();
    TestBed.configureTestingModule({
      providers: [AuthService, provideRouter([])],
    });
    auth = TestBed.inject(AuthService);
  });

  it('signs in the administrator and grants admin access', () => {
    expect(auth.signIn('admin', '123')).toBe(true);
    expect(auth.isLoggedIn()).toBe(true);
    expect(auth.isAdmin()).toBe(true);
    expect(localStorage.getItem('bike-user')).toBe('admin');
  });

  it('signs in Youssef without admin access', () => {
    expect(auth.signIn('youssef', '123')).toBe(true);
    expect(auth.isLoggedIn()).toBe(true);
    expect(auth.isAdmin()).toBe(false);
  });

  it('rejects invalid credentials', () => {
    expect(auth.signIn('admin', 'wrong')).toBe(false);
    expect(auth.isLoggedIn()).toBe(false);
  });
});
