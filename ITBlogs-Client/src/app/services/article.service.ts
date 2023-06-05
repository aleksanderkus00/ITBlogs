import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { Article } from '../models/article.model';
import { environment } from 'src/environments/environment';
import { UserService } from './user.service';
import { PaginatedResult } from '../models/paginated-result.model';

@Injectable({
  providedIn: 'root',
})
export class ArticleService {
  private env = environment;

  constructor(private http: HttpClient, private userService: UserService) {}

  public getArticleById(id: number): Observable<Article> {
    return this.http.get<Article>(`${this.env.apiUrl}/article/${id}`);
  }

  public getAllArticles(
    pageNumber = 0,
    pageSize = 10 // TODO: set back to 100
  ): Observable<PaginatedResult<Article[]>> {
    return this.http.get<PaginatedResult<Article[]>>(
      `${this.env.apiUrl}/article/all-articles/${pageNumber}/${pageSize}`
    );
  }

  public getYourArticles(
    pageNumber = 0,
    pageSize = 100
  ): Observable<Article[]> {
    const userId = this.userService.getUserId();
    return this.http.get<Article[]>(
      `${this.env.apiUrl}/article/your-articles/${userId}/${pageNumber}/${pageSize}`
    );
  }

  public getNewsArticles(
    pageNumber = 0,
    pageSize = 100
  ): Observable<Article[]> {
    return this.http.get<Article[]>(
      `${this.env.apiUrl}/article/news-articles/${pageNumber}/${pageSize}`
    );
  }

  public createArticle(article: Article, userId: number): Observable<boolean> {
    const data = JSON.stringify(article);
    const options = { headers: { 'Content-Type': 'application/json' } };
    return this.http.post<boolean>(
      `${this.env.apiUrl}/article/${userId}`,
      data,
      options
    );
  }

  public likeArticle(articleId: number, userId: number): Observable<boolean> {
    return this.http.put<boolean>(
      `${this.env.apiUrl}/article/like/${userId}/${articleId}`,
      ''
    );
  }

  public unlikeArticle(articleId: number, userId: number): Observable<boolean> {
    return this.http.put<boolean>(
      `${this.env.apiUrl}/article/unlike/${userId}/${articleId}`,
      ''
    );
  }

  public getLikedArticles(
    userId: number,
    pageNumber = 0,
    pageSize = 100
  ): Observable<Article[]> {
    return this.http.get<Article[]>(
      `${this.env.apiUrl}/article/liked/${userId}/${pageNumber}/${pageSize}`
    );
  }

  public saveArticle(articleId: number, userId: number): Observable<boolean> {
    return this.http.put<boolean>(
      `${this.env.apiUrl}/article/save/${userId}/${articleId}`,
      ''
    );
  }

  public unsaveArticle(articleId: number, userId: number): Observable<boolean> {
    return this.http.put<boolean>(
      `${this.env.apiUrl}/article/unsave/${userId}/${articleId}`,
      ''
    );
  }

  public getSavedArticles(
    userId: number,
    pageNumber = 0,
    pageSize = 100
  ): Observable<Article[]> {
    return this.http.get<Article[]>(
      `${this.env.apiUrl}/article/saved/${userId}/${pageNumber}/${pageSize}`
    );
  }
}
