export class User {
  token?: string;
  username?: string;
  authorities?: string;

  isAdmin(): boolean {
    return this.authorities?.includes("ROLE_ADMIN") ?? false;
  }
}
