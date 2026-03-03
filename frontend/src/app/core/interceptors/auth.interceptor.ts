import { HttpInterceptorFn } from '@angular/common/http';

// Attaches Basic Auth credentials to every API request.
// In a production app this would use a token from a proper auth service.
export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const credentials = btoa('admin:admin');
  const authReq = req.clone({
    setHeaders: { Authorization: `Basic ${credentials}` }
  });
  return next(authReq);
};
