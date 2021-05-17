package demograils

import demograils.exception.BadRequestException

trait Pagination {

    def validateParamsForPagination(def messageSource, def params) throws BadRequestException {
        if (params.max instanceof String) {
            try {
                params.max = Integer.parseInt(params.max)
            } catch(NumberFormatException ignored) {
                throw new BadRequestException(messageSource.getMessage("pagination.max_must_be_a_number", null, Locale.getDefault()))
            }
        }
        if (params.page instanceof String) {
            try {
                params.page = Integer.parseInt(params.page)
            } catch(NumberFormatException ignored) {
                throw new BadRequestException(messageSource.getMessage("pagination.page_must_be_a_number", null, Locale.getDefault()))
            }
        }
        if (params.max < 0) {
            throw new BadRequestException(messageSource.getMessage("pagination.max_can_not_be_less_than_zero", null, Locale.getDefault()))
        }
        if (params.page < 0) {
            throw new BadRequestException(messageSource.getMessage("pagination.page_can_not_be_less_than_zero", null, Locale.getDefault()))
        }
    }
}