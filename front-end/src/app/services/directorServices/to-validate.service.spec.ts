import { TestBed } from '@angular/core/testing';

import { ToValidateService } from './to-validate.service';

describe('ToValidateService', () => {
  let service: ToValidateService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ToValidateService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
