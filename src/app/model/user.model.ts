export interface User {
  username: string;
  password: string;
  roles: ('ADMIN' | 'USER')[];
}