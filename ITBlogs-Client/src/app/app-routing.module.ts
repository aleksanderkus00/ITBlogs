import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PostsComponent } from './components/posts/posts.component';
import { NewsComponent } from './components/news/news.component';
import { DiscoverComponent } from './components/discover/discover.component';
import { SettingsComponent } from './components/settings/settings.component';
import { CreateArticleComponent } from './components/create-article/create-article.component';
import { NotFoundComponent } from './components/errors/not-found/not-found.component';
import { PostDetailsComponent } from './components/post-details/post-details.component';
import { SavedComponent } from './components/saved/saved.component';
import { YourArticlesComponent } from './components/your-articles/your-articles.component';
import { authGuard } from './guard/auth.guard';

const routes: Routes = [
  {
    path: '',
    redirectTo: '/posts',
    pathMatch: 'full',
  },
  { path: 'posts', component: PostsComponent },
  {
    path: 'post-details/:id',
    component: PostDetailsComponent,
  },
  { path: 'news', component: NewsComponent },
  { path: 'discover', component: DiscoverComponent },
  {
    path: 'your-articles',
    component: YourArticlesComponent,
    canActivate: [authGuard],
  },
  { path: 'saved', component: SavedComponent, canActivate: [authGuard] },
  { path: 'settings', component: SettingsComponent, canActivate: [authGuard] },
  {
    path: 'create-article',
    component: CreateArticleComponent,
    canActivate: [authGuard],
  },
  { path: '**', component: NotFoundComponent, pathMatch: 'full' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
