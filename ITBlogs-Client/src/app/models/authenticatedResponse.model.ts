export interface AuthenticatedResponse {
  id: number;
  token: string;
  type: string;
  email: string;
  roles: string[];
}
