package es.udc.ws.movies.model.sale;

import java.util.Calendar;

public class Sale {

    private Long saleId;
    private Long movieId;
    private String userId;
    private Calendar expirationDate;
    private String creditCardNumber;
    private float price;
    private String movieUrl;
    private Calendar saleDate;

    public Sale(Long movieId, String userId, Calendar expirationDate,
            String creditCardNumber, float price, String movieUrl,
            Calendar saleDate) {
        this.movieId = movieId;
        this.userId = userId;
        this.expirationDate = expirationDate;
        this.creditCardNumber = creditCardNumber;
        if (expirationDate != null) {
            this.expirationDate.set(Calendar.MILLISECOND, 0);
        }
        this.price = price;
        this.movieUrl = movieUrl;
        this.saleDate = saleDate;
        if (saleDate != null) {
            this.saleDate.set(Calendar.MILLISECOND, 0);
        }
    }

    public Sale(Long saleId, Long movieId, String userId,
            Calendar expirationDate, String creditCardNumber, float price,
            String movieUrl, Calendar saleDate) {
        this(movieId, userId, expirationDate, creditCardNumber, price,
                movieUrl, saleDate);
        this.saleId = saleId;
    }

    public Long getSaleId() {
        return saleId;
    }

    public void setSaleId(Long saleId) {
        this.saleId = saleId;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Calendar getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Calendar expirationDate) {
        this.expirationDate = expirationDate;
        if (expirationDate != null) {
            this.expirationDate.set(Calendar.MILLISECOND, 0);
        }
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getMovieUrl() {
        return movieUrl;
    }

    public void setMovieUrl(String movieUrl) {
        this.movieUrl = movieUrl;
    }

    public Calendar getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(Calendar saleDate) {
        this.saleDate = saleDate;
        if (saleDate != null) {
            this.saleDate.set(Calendar.MILLISECOND, 0);
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime
                * result
                + ((creditCardNumber == null) ? 0 : creditCardNumber.hashCode());
        result = prime * result
                + ((expirationDate == null) ? 0 : expirationDate.hashCode());
        result = prime * result + ((movieId == null) ? 0 : movieId.hashCode());
        result = prime * result
                + ((movieUrl == null) ? 0 : movieUrl.hashCode());
        result = prime * result + Float.floatToIntBits(price);
        result = prime * result
                + ((saleDate == null) ? 0 : saleDate.hashCode());
        result = prime * result + ((saleId == null) ? 0 : saleId.hashCode());
        result = prime * result + ((userId == null) ? 0 : userId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Sale other = (Sale) obj;
        if (creditCardNumber == null) {
            if (other.creditCardNumber != null) {
                return false;
            }
        } else if (!creditCardNumber.equals(other.creditCardNumber)) {
            return false;
        }
        if (expirationDate == null) {
            if (other.expirationDate != null) {
                return false;
            }
        } else if (!expirationDate.equals(other.expirationDate)) {
            return false;
        }
        if (movieId == null) {
            if (other.movieId != null) {
                return false;
            }
        } else if (!movieId.equals(other.movieId)) {
            return false;
        }
        if (movieUrl == null) {
            if (other.movieUrl != null) {
                return false;
            }
        } else if (!movieUrl.equals(other.movieUrl)) {
            return false;
        }
        if (Float.floatToIntBits(price) != Float.floatToIntBits(other.price)) {
            return false;
        }
        if (saleDate == null) {
            if (other.saleDate != null) {
                return false;
            }
        } else if (!saleDate.equals(other.saleDate)) {
            return false;
        }
        if (saleId == null) {
            if (other.saleId != null) {
                return false;
            }
        } else if (!saleId.equals(other.saleId)) {
            return false;
        }
        if (userId == null) {
            if (other.userId != null) {
                return false;
            }
        } else if (!userId.equals(other.userId)) {
            return false;
        }
        return true;
    }
}
