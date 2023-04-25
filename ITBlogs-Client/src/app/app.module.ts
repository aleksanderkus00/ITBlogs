import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { JwtModule } from '@auth0/angular-jwt';
import { environment } from 'src/environments/environment';
import { NavbarComponent } from './components/navbar/navbar.component';
import { SearchBarComponent } from './components/search-bar/search-bar.component';
import { MatButtonModule } from '@angular/material/button';
import { PostComponent } from './components/post/post.component';
import { PostsComponent } from './components/posts/posts.component';
import { ScrollingModule } from '@angular/cdk/scrolling';
import { NewsComponent } from './components/news/news.component';
import { DiscoverComponent } from './components/discover/discover.component';
import { SavedComponent } from './components/saved/saved.component';
import { SettingsComponent } from './components/settings/settings.component';
import { MatDialogModule } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { FormsModule } from '@angular/forms';
import { SingInDialogComponent } from './components/dialogs/sing-in-dialog/sing-in-dialog.component';
import { SingUpDialogComponent } from './components/dialogs/sing-up-dialog/sing-up-dialog.component';
import { MatInputModule } from '@angular/material/input';
import { CreateArticleComponent } from './components/create-article/create-article.component';
import { NotFoundComponent } from './components/errors/not-found/not-found.component';
import { HttpClientModule } from '@angular/common/http';
import { CKEditorModule } from '@ckeditor/ckeditor5-angular';
import { PostDetailsComponent } from './components/post-details/post-details.component';

export function tokenGetter() {
  return localStorage.getItem('access_token');
}

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    SearchBarComponent,
    PostComponent,
    PostsComponent,
    NewsComponent,
    DiscoverComponent,
    SavedComponent,
    SettingsComponent,
    SingInDialogComponent,
    SingUpDialogComponent,
    CreateArticleComponent,
    NotFoundComponent,
    PostDetailsComponent,
  ],
  imports: [
    CKEditorModule,
    HttpClientModule,
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatButtonModule,
    ScrollingModule,
    MatDialogModule,
    MatFormFieldModule,
    FormsModule,
    MatInputModule,
    JwtModule.forRoot({
      config: {
        tokenGetter: tokenGetter,
        allowedDomains: [environment.allowedDomains],
        disallowedRoutes: [''],
      },
    }),
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
