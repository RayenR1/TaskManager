import { TestBed } from '@angular/core/testing';

import { DetaillesUserService } from './detailles-user.service';

describe('DetaillesUserService', () => {
  let service: DetaillesUserService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DetaillesUserService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
