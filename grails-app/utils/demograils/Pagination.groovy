package demograils

import demograils.exception.BadRequestException

trait Pagination {

    def handleParamsForPagination(def messageSource, def params) throws BadRequestException {
        if (params.max instanceof String) {
            try {
                params.max = Integer.parseInt(params.max)
            } catch(NumberFormatException ignored) {
                throw new BadRequestException(messageSource.getMessage("pagination.max_must_be_a_number", null, Locale.getDefault()))
            }
        }
        if (params.offset instanceof String) {
            try {
                params.offset = Integer.parseInt(params.offset)
            } catch(NumberFormatException ignored) {
                throw new BadRequestException(messageSource.getMessage("pagination.offset_must_be_a_number", null, Locale.getDefault()))
            }
        }
        if (params.max < 0) {
            throw new BadRequestException(messageSource.getMessage("pagination.max_can_not_be_less_than_zero", null, Locale.getDefault()))
        }
        if (params.offset < 0) {
            throw new BadRequestException(messageSource.getMessage("pagination.offset_can_not_be_less_than_zero", null, Locale.getDefault()))
        }
    }
}