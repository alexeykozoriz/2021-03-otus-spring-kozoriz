export class User {
  auth?: string;
  username?: string;
  authorities?: string;

  isAdmin(): boolean {
    return this.authorities?.includes("ROLE_ADMIN") ?? false;
  }
}
