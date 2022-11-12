import { TestBed } from '@angular/core/testing';

import { CoordenadasService } from './coordenadas.service';

describe('CoordenadasService', () => {
  let service: CoordenadasService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CoordenadasService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
