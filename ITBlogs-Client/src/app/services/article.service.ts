import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Article } from '../models/article.model';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class ArticleService {
  private env = environment;

  constructor(private http: HttpClient) {}

  public getArticleById(id: number): Observable<Article> {
    return this.http.get<Article>(`${this.env.apiUrl}/article/${id}`);
  }

  public getAllArticles(): Observable<Article[]> {
    return this.http.get<Article[]>(
      `${this.env.apiUrl}/article/all-articles/0/100`
    );
  }
}
