import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class MovieService {

  private apiUrl = 'http://localhost:8080/api/';  

  constructor(private http: HttpClient) {}

  searchMovies(query: string, currentPage: number): Observable<any> {
    const token = sessionStorage.getItem('authToken');
    let headers = new HttpHeaders();
    if (token) {
      headers = headers.set('Authorization', `Bearer ${token}`);
    }

    const params = new HttpParams().set('page', currentPage);

    return this.http.get<any>(this.apiUrl+'omdb/search/'+query, { 
      headers, 
      params
    });
  }

  importMovie(movieData: any){
    const token = sessionStorage.getItem('authToken'); 

    let headers = new HttpHeaders();
    if (token) {
      headers = headers.set('Authorization', `Bearer ${token}`);
    }
    return this.http.post<any>(this.apiUrl+'movies/import/imdb', movieData, { headers });
  }

  removeMovie(movieImdbID: any){
    const token = sessionStorage.getItem('authToken'); 

    let headers = new HttpHeaders();
    if (token) {
      headers = headers.set('Authorization', `Bearer ${token}`);
    }
    return this.http.delete<any>(this.apiUrl+'movies/remove/imdb/'+ movieImdbID, { headers });
  }

  getMovies(): Observable<any> {

    const token = sessionStorage.getItem('authToken');

    let headers = new HttpHeaders();
    if (token) {
      headers = headers.set('Authorization', `Bearer ${token}`);
    }

    return this.http.get<any>(this.apiUrl+'movies', { headers });
  }

  getPaginatedMovies(payload: any): Observable<any> {

    const token = sessionStorage.getItem('authToken'); 

    let headers = new HttpHeaders();
    if (token) {
      headers = headers.set('Authorization', `Bearer ${token}`);
    }

    return this.http.post<any>(this.apiUrl+'paged-movies', payload, { headers });
  }


  getMovieById(id: string): Observable<any> {
    const token = sessionStorage.getItem('authToken');
    let headers = new HttpHeaders();
    if (token) {
      headers = headers.set('Authorization', `Bearer ${token}`);
    }

    return this.http.get<any>(this.apiUrl+ 'movies/'+ id, { headers });
  }

  removeMovieById(movieId: any){
    const token = sessionStorage.getItem('authToken'); 

    let headers = new HttpHeaders();
    if (token) {
      headers = headers.set('Authorization', `Bearer ${token}`);
    }
    return this.http.delete<any>(this.apiUrl+'movies/'+ movieId, { headers });
  }
  
}