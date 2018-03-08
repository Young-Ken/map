package com.caobugs.map.layer.tile.downtile.tileurl;

import com.caobugs.map.layer.tile.downtile.factory.IURLEnum;

import java.util.Random;

public class TDTUrl implements BaseTiledURL {
    private TDTTiledType mapServiceType;

    public TDTUrl(TDTTiledType mapServiceType) {
        this.mapServiceType = mapServiceType;
    }

    @Override
    public String getTiledServiceURL(int level, int col, int row) {
        StringBuilder url = new StringBuilder("http://t");
        Random random = new Random();
        int subdomain = (random.nextInt(6) + 1);
        url.append(subdomain);
        switch (this.mapServiceType) {
            case TDT_VECTOR:
                url.append(".tianditu.com/DataServer?T=vec_c&X=").append(col).append("&Y=").append(row).append("&L=").append(level);
                //url.append(".tianditu.com/vec_c/wmts?request=GetTile&service=wmts&version=1.0.0&layer=vec&style=default&format=tiles&TileMatrixSet=c&TILECOL=").append(this._col).append("&TILEROW=").append(this._row).append("&TILEMATRIX=").append(this._level);
                //url.append(".tianditu.com/DataServer?T=vec_w&X=").append(this._col).append("&Y=").append(this._row).append("&L=").append(this._level);
                break;
            case TDT_VECTOR_BZ:
                url.append(".tianditu.com/DataServer?T=cva_c&X=").append(col).append("&Y=").append(row).append("&L=").append(level);
                //url.append(".tianditu.com/DataServer?T=cva_w&X=").append(this._col).append("&Y=").append(this._row).append("&L=").append(this._level);
                break;
            case TDT_IMAGE_BZ:
                url.append(".tianditu.com/DataServer?T=cia_c&X=").append(col).append("&Y=").append(row).append("&L=").append(level);
                break;
            case TDT_IMAGE:
                url.append(".tianditu.com/DataServer?T=img_c&X=").append(col).append("&Y=").append(row).append("&L=").append(level);
                break;
            default:
                return null;
        }
        return url.toString();
    }

    @Override
    public IURLEnum getMapServiceType() {
        return mapServiceType;
    }
}
